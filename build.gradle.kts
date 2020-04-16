import org.ajoberstar.grgit.Grgit
import net.thauvin.erik.gradle.semver.SemverIncrementTask

plugins {
    java
    id("org.ajoberstar.grgit") version "4.0.0"
    id("com.github.hierynomus.license") version "0.15.0"
    id("net.thauvin.erik.gradle.semver") version "1.0.4"
    id("com.github.ben-manes.versions").version("0.28.0")
}

group = "com.github.fuzzdbunit"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-params:5.5.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.assertj:assertj-core:3.14.0")
    testImplementation("org.mockito:mockito-junit-jupiter:3.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

license {
    excludes(listOf("**/*.txt", "**/*.html"))
}

tasks.test {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
    classpath.plus("$buildDir/resources/test")

    filter {
        //include tests
        includeTestsMatching("*Test")
    }
}

tasks.register("release") {
    doFirst() {
        println("Deploy to maven Central")
    }
    doLast() {
        println("Incrementing version..")
        tasks.incrementPatch.get().increment()
        println("Opening git..."+System.getenv("GRGIT_USER")+":"+System.getenv("GRGIT_PASS"))
        val grgit = Grgit.open(mapOf("dir" to "$projectDir/.git"))
        println("Adding version.properties")
        grgit.add(mapOf("patterns" to listOf("version.properties")))
        println("Commiting...")
        grgit.commit(mapOf("message" to "Update version"))
        println("Pushing...")
        grgit.push()
        println("Finished updating version.properties")
    }
}

val cloneOrUpdateFuzzDb by tasks.registering {
    println("buildDir: $buildDir")
    doLast {
        if (File("$buildDir/fuzzDb").exists()) {
            println("Update fuzzDB folder")
            val grgit = Grgit.open(mapOf("dir" to "$buildDir/fuzzDb"))
            grgit.pull()
        } else {
            println("Clone fuzzDB folder")
            val grgit = Grgit.clone(mapOf("dir" to "$buildDir/fuzzDb", "uri" to "https://github.com/fuzzdb-project/fuzzdb.git"))
            val checkoutArgs = mapOf("branch" to "master")
            grgit.checkout(checkoutArgs)
        }
    }
}

val GENERATED_RESOURCE_FOLDER = "$buildDir/generated/resources"
val RESOURCE_ATTACK_FOLDER = "$GENERATED_RESOURCE_FOLDER/attack"
val GENERATED_JAVA_FOLDER = "$buildDir/generated/java"

val copyFuzzDbFiles by tasks.registering {
    dependsOn(cloneOrUpdateFuzzDb)
    println("Copy to $RESOURCE_ATTACK_FOLDER")

    doLast {
        copy {
            from(fileTree("$buildDir/fuzzDb/attack"))
            into("$RESOURCE_ATTACK_FOLDER")
        }
    }
}

val generateFuzzEnum by tasks.registering {
    dependsOn(copyFuzzDbFiles)

    doLast {
        val generator = FuzzDbEnumGenerator()
        generator.generateEnum("$RESOURCE_ATTACK_FOLDER")
    }
}

sourceSets["main"].java {
    srcDirs("$GENERATED_JAVA_FOLDER")
}

sourceSets["main"].resources {
    srcDirs("$GENERATED_RESOURCE_FOLDER")
}

tasks.compileJava {
    dependsOn(tasks.licenseFormat)
    dependsOn(generateFuzzEnum)
}

class FuzzDbEnumGenerator {

    private val SOURCE_FILEPATH = "$projectDir/src/main/templates/FuzzFile.java"
    private val TARGET_FOLDER = "$GENERATED_JAVA_FOLDER/com/github/fuzzdbunit/params/provider/"
    private val TARGET_FILENAME = "FuzzFile.java"

    fun generateEnum(path: String): Boolean {
        println("Path: " + path)
        // create reader
        val reader = File(SOURCE_FILEPATH).bufferedReader()
        // create writer
        val targetFolder = File(TARGET_FOLDER)
        targetFolder.mkdirs()
        val writer = File(targetFolder, TARGET_FILENAME).bufferedWriter()

        val enumNames = File(path)
                .walk()
                .filter { it.isFile }
                .filter { it.canonicalPath.contains(".txt") }
                .filter { !it.canonicalPath.contains(".doc.txt") }
                .map { buildEnumName(it) }
                .toMap()
        val state = State()

        reader.forEachLine { l -> processLine(l, state, writer, enumNames) }
        reader.close()
        writer.close()
        return true
    }

    private fun processLine(l: String, state: State, writer: java.io.BufferedWriter, enumNames: Map<String, String>) {
        if (state.mustCopy(l)) {
            writer.write(l)
            writer.newLine()
        } else if (state.mustAppendEnums()) {
            val nameIt = enumNames.iterator()
            for ((enumName, filename) in nameIt) {
                writer.write("  $enumName(\"$filename\")")
                if (nameIt.hasNext()) {
                    writer.append(",")
                } else {
                    writer.append(";")
                }
                writer.newLine()
            }
        }
    }

    private fun buildEnum(enumName: String, filename: String): String {
        val fuzzEnum = "  $enumName(\"$filename\")"
        return fuzzEnum
    }

    fun buildEnumName(f: File): Pair<String, String> {
        val relativePath = f.canonicalPath
                .substringAfter("resources" + File.separatorChar)
                .replace("\\", "/")
        val enumName = relativePath
                .substringAfter("attack/")
                .substringBefore(".txt")
                .replace("-", "_")
                .replace("/", "_")
                .replace(".", "_")
                .toUpperCase()
        val p = Pair<String, String>(enumName, relativePath)
        return p
    }

    class State {
        private var partNumber = 1
        private var mustAppend = false

        fun mustAppendEnums(): Boolean {
            if (this.mustAppend) {
                this.mustAppend = false
                return true
            }
            return false
        }

        fun mustCopy(line: String): Boolean {
            if (partNumber == 1) {
                if (line.contains("Begin of list!")) {
                    partNumber++
                    mustAppend = true
                }
                return true
            }
            if (partNumber == 2) {
                if (line.contains(";")) {
                    partNumber++
                }
                return false
            }
            return true
        }
    }

}
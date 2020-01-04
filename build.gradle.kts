import org.ajoberstar.grgit.Grgit

plugins {
    java
    id("org.ajoberstar.grgit") version "4.0.0"
    id("com.github.hierynomus.license") version "0.15.0"
}

group = "com.github.fuzzdbunit"
version = "0.1-SNAPSHOT"

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
    exclude("**/*.txt")
}

tasks.test {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
    classpath.plus("build/resources/test")

    filter {
        //include tests
        includeTestsMatching("*Test")
    }
}

val cloneOrUpdateFuzzDb by tasks.registering {
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

val copyFuzzDbFiles by tasks.registering {
    dependsOn(cloneOrUpdateFuzzDb)

    doLast {
        copy {
            from(fileTree("$buildDir/fuzzDb/attack"))
            into("./src/main/resources")
        }
    }
}

val generateFuzzEnum by tasks.registering {
    dependsOn(copyFuzzDbFiles)

    doLast {
        val generator = FuzzDbEnumGenerator()
        generator.generateEnum("src/main/resources")
    }
}

tasks.compileJava {
    dependsOn(tasks.licenseFormat)
}

class FuzzDbEnumGenerator {

    private val SOURCE_FILEPATH = "src/main/java/com/github/fuzzdbunit/params/provider/FuzzFile.java"
    private val TARGET_FILEPATH = "src/main/java/com/github/fuzzdbunit/params/provider/FuzzFile.java.tmp"

    fun generateEnum(path: String): Boolean {
        println("Path: " + path)
        val sourceFile = File(SOURCE_FILEPATH)
        val targetFile = File(TARGET_FILEPATH)
        val reader = sourceFile.bufferedReader()
        val writer = targetFile.bufferedWriter()
        val enumNames = File(path)
                .walk()
                .filter { it.isFile }
                .filter { it.canonicalPath.contains(".txt") }
                .map { buildEnumName(it) }
                .toMap()
        enumNames.forEach { it -> println(it) }
        val state = State()

        reader.forEachLine {
            if (state.mustCopy(it)) {
                writer.write(it)
                writer.newLine()
            } else if (state.mustAppendEnums()) {
                val it = enumNames.iterator()
                for ((enumName, filename) in it) {
                    writer.write("  $enumName(\"$filename\")")
                    if (it.hasNext()) {
                        writer.append(",")
                    } else {
                        writer.append(";")
                    }
                    writer.newLine()
                }
            }
        }
        writer.close()
        targetFile.copyTo(sourceFile, true)
        return true
    }

    private fun buildEnum(enumName: String, filename: String): String {
        var fuzzEnum = "  $enumName(\"$filename\")"
        return fuzzEnum
    }

    fun buildEnumName(f: File): Pair<String, String> {
        val relativePath = f.canonicalPath
                .substringAfter("resources\\")
                .replace("\\", "/")
        val enumName = relativePath
                .substringAfter("attack.")
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
                if (line.contains("public enum FuzzFile")) {
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
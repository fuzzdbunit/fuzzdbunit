/**
 * Copyright (C)2020 - Patrick M.J. Roth <red.parrot@bluewin.ch>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.fuzzdbunit.params.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Enum representing the fuzz data files defined by FuzzDB.
 */
public enum FuzzFile {
    /* Developer note: the list below is generated in the build.gradle script and will be regenerated
       during the next build. So please do not edit this list.
       Begin of list!    */
    XML_XML_ATTACKS("xml/xml-attacks.txt"),
    MIMETYPES_MIMETYPES("mimetypes/MimeTypes.txt");
    /* End of generated list.   */

    private List<String> data;
    private String filePath;

    FuzzFile(String filePath) {
        this.filePath = filePath;
    }

    public int size() {
        return getData().size();
    }

    public String getFilePath() {
        return this.filePath;
    }

    public List<String> getData() {
        if (data == null) {
            data = new ArrayList<String>();
            URL u = this.getClass().getClassLoader().getResource(filePath);
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(filePath);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                while (br.ready()) {
                    data.add(br.readLine());
                }
            } catch (IOException e) {
                throw new RuntimeException("Can't load data from " + this.filePath, e);
            }
        }
        return data;
    }

}

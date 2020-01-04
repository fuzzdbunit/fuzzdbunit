/**
 * Copyright (C)2020 - Patrick M.J. Roth <red.parrot@bluewin.ch>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
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
  ALL_ATTACKS_ALL_ATTACKS_UNIX("all-attacks/all-attacks-unix.txt"),
  ALL_ATTACKS_ALL_ATTACKS_WIN("all-attacks/all-attacks-win.txt"),
  ALL_ATTACKS_ALL_ATTACKS_XPLATFORM("all-attacks/all-attacks-xplatform.txt"),
  AUTHENTICATION_PHP_MAGIC_HASHES_FUZZ("authentication/php_magic_hashes.fuzz.txt"),
  BUSINESS_LOGIC_COMMONDEBUGPARAMNAMES("business-logic/CommonDebugParamNames.txt"),
  BUSINESS_LOGIC_COMMONMETHODNAMES("business-logic/CommonMethodNames.txt"),
  BUSINESS_LOGIC_DEBUGPARAMS_JSON_FUZZ("business-logic/DebugParams.Json.fuzz.txt"),
  CONTROL_CHARS_HEXVALSALLBYTES("control-chars/HexValsAllBytes.txt"),
  CONTROL_CHARS_IMESSAGE("control-chars/imessage.txt"),
  CONTROL_CHARS_NULLBYTEREPRESENTATIONS("control-chars/NullByteRepresentations.txt"),
  CONTROL_CHARS_TERMINAL_ESCAPE_CODES("control-chars/terminal-escape-codes.txt"),
  CONTROL_CHARS_TRUE("control-chars/true.txt"),
  DISCLOSURE_DIRECTORY_DIRECTORY_INDEXING_GENERIC("disclosure-directory/directory-indexing-generic.txt"),
  DISCLOSURE_LOCALPATHS_UNIX_COMMON_UNIX_HTTPD_LOG_LOCATIONS("disclosure-localpaths/unix/common-unix-httpd-log-locations.txt"),
  DISCLOSURE_SOURCE_SOURCE_DISC_CMD_EXEC_TRAVERSAL("disclosure-source/source-disc-cmd-exec-traversal.txt"),
  DISCLOSURE_SOURCE_SOURCE_DISCLOSURE_GENERIC("disclosure-source/source-disclosure-generic.txt"),
  DISCLOSURE_SOURCE_SOURCE_DISCLOSURE_MICROSOFT("disclosure-source/source-disclosure-microsoft.txt"),
  EMAIL_INVALID_EMAIL_ADDRESSES("email/invalid-email-addresses.txt"),
  EMAIL_VALID_EMAIL_ADDRESSES("email/valid-email-addresses.txt"),
  FILE_UPLOAD_ALT_EXTENSIONS_ASP("file-upload/alt-extensions-asp.txt"),
  FILE_UPLOAD_ALT_EXTENSIONS_COLDFUSION("file-upload/alt-extensions-coldfusion.txt"),
  FILE_UPLOAD_ALT_EXTENSIONS_JSP("file-upload/alt-extensions-jsp.txt"),
  FILE_UPLOAD_ALT_EXTENSIONS_PERL("file-upload/alt-extensions-perl.txt"),
  FILE_UPLOAD_ALT_EXTENSIONS_PHP("file-upload/alt-extensions-php.txt"),
  FILE_UPLOAD_FILE_UL_FILTER_BYPASS_COMMONLY_WRITABLE_DIRECTORIES("file-upload/file-ul-filter-bypass-commonly-writable-directories.txt"),
  FILE_UPLOAD_FILE_UL_FILTER_BYPASS_MICROSOFT_ASP_FILETYPE_BF("file-upload/file-ul-filter-bypass-microsoft-asp-filetype-bf.txt"),
  FILE_UPLOAD_FILE_UL_FILTER_BYPASS_MICROSOFT_ASP("file-upload/file-ul-filter-bypass-microsoft-asp.txt"),
  FILE_UPLOAD_FILE_UL_FILTER_BYPASS_MS_PHP("file-upload/file-ul-filter-bypass-ms-php.txt"),
  FILE_UPLOAD_FILE_UL_FILTER_BYPASS_X_PLATFORM_GENERIC("file-upload/file-ul-filter-bypass-x-platform-generic.txt"),
  FILE_UPLOAD_FILE_UL_FILTER_BYPASS_X_PLATFORM_PHP("file-upload/file-ul-filter-bypass-x-platform-php.txt"),
  FILE_UPLOAD_INVALID_FILENAMES_LINUX("file-upload/invalid-filenames-linux.txt"),
  FILE_UPLOAD_INVALID_FILENAMES_MICROSOFT("file-upload/invalid-filenames-microsoft.txt"),
  FILE_UPLOAD_INVALID_FILESYSTEM_CHARS_MICROSOFT("file-upload/invalid-filesystem-chars-microsoft.txt"),
  FILE_UPLOAD_INVALID_FILESYSTEM_CHARS_OSX("file-upload/invalid-filesystem-chars-osx.txt"),
  FILE_UPLOAD_MALICIOUS_IMAGES_EICAR_COM("file-upload/malicious-images/eicar.com.txt"),
  FORMAT_STRINGS_FORMAT_STRINGS("format-strings/format-strings.txt"),
  HTML_JS_FUZZ_HTML5SEC_INJECTIONS("html_js_fuzz/HTML5sec_Injections.txt"),
  HTML_JS_FUZZ_HTML_ATTRIBUTES("html_js_fuzz/html_attributes.txt"),
  HTML_JS_FUZZ_HTML_TAGS("html_js_fuzz/html_tags.txt"),
  HTML_JS_FUZZ_JAVASCRIPT_EVENTS("html_js_fuzz/javascript_events.txt"),
  HTML_JS_FUZZ_JS_INJECT("html_js_fuzz/js_inject.txt"),
  HTML_JS_FUZZ_QUOTATIONMARKS("html_js_fuzz/quotationmarks.txt"),
  HTTP_PROTOCOL_CRLF_INJECTION("http-protocol/crlf-injection.txt"),
  HTTP_PROTOCOL_HPP("http-protocol/hpp.txt"),
  HTTP_PROTOCOL_HTTP_HEADER_CACHE_POISON("http-protocol/http-header-cache-poison.txt"),
  HTTP_PROTOCOL_HTTP_PROTOCOL_METHODS("http-protocol/http-protocol-methods.txt"),
  HTTP_PROTOCOL_HTTP_REQUEST_HEADER_FIELD_NAMES("http-protocol/http-request-header-field-names.txt"),
  HTTP_PROTOCOL_HTTP_RESPONSE_HEADER_FIELD_NAMES("http-protocol/http-response-header-field-names.txt"),
  HTTP_PROTOCOL_KNOWN_URI_TYPES("http-protocol/known-uri-types.txt"),
  HTTP_PROTOCOL_USER_AGENTS("http-protocol/user-agents.txt"),
  INTEGER_OVERFLOW_INTEGER_OVERFLOWS("integer-overflow/integer-overflows.txt"),
  IP_LOCALHOST("ip/localhost.txt"),
  JSON_JSON_FUZZING("json/JSON_Fuzzing.txt"),
  LDAP_LDAP_INJECTION("ldap/ldap-injection.txt"),
  LFI_COMMON_MS_HTTPD_LOG_LOCATIONS("lfi/common-ms-httpd-log-locations.txt"),
  LFI_COMMON_UNIX_HTTPD_LOG_LOCATIONS("lfi/common-unix-httpd-log-locations.txt"),
  LFI_JHADDIX_LFI("lfi/JHADDIX_LFI.txt"),
  MIMETYPES_MIMETYPES("mimetypes/MimeTypes.txt"),
  NO_SQL_INJECTION_MONGODB("no-sql-injection/mongodb.txt"),
  OS_CMD_EXECUTION_COMMAND_EXECUTION_UNIX("os-cmd-execution/command-execution-unix.txt"),
  OS_CMD_EXECUTION_COMMAND_INJECTION_TEMPLATE("os-cmd-execution/command-injection-template.txt"),
  OS_CMD_EXECUTION_COMMANDS_LINUX("os-cmd-execution/Commands-Linux.txt"),
  OS_CMD_EXECUTION_COMMANDS_OSX("os-cmd-execution/Commands-OSX.txt"),
  OS_CMD_EXECUTION_COMMANDS_WINDOWS("os-cmd-execution/Commands-Windows.txt"),
  OS_CMD_EXECUTION_COMMANDS_WINDOWSPOWERSHELL("os-cmd-execution/Commands-WindowsPowershell.txt"),
  OS_CMD_EXECUTION_OSCOMMANDINJECT_WINDOWS("os-cmd-execution/OSCommandInject.Windows.txt"),
  OS_CMD_EXECUTION_SHELL_DELIMITERS("os-cmd-execution/shell-delimiters.txt"),
  OS_CMD_EXECUTION_SHELL_OPERATORS("os-cmd-execution/shell-operators.txt"),
  OS_CMD_EXECUTION_SOURCE_DISC_CMD_EXEC_TRAVERSAL("os-cmd-execution/source-disc-cmd-exec-traversal.txt"),
  OS_CMD_EXECUTION_USEFUL_COMMANDS_UNIX("os-cmd-execution/useful-commands-unix.txt"),
  OS_CMD_EXECUTION_USEFUL_COMMANDS_WINDOWS("os-cmd-execution/useful-commands-windows.txt"),
  OS_DIR_INDEXING_DIRECTORY_INDEXING("os-dir-indexing/directory-indexing.txt"),
  PATH_TRAVERSAL_PATH_TRAVERSAL_WINDOWS("path-traversal/path-traversal-windows.txt"),
  PATH_TRAVERSAL_TRAVERSALS_8_DEEP_EXOTIC_ENCODING("path-traversal/traversals-8-deep-exotic-encoding.txt"),
  REDIRECT_REDIRECT_INJECTION_TEMPLATE("redirect/redirect-injection-template.txt"),
  REDIRECT_REDIRECT_URLS_TEMPLATE("redirect/redirect-urls-template.txt"),
  RFI_RFI("rfi/rfi.txt"),
  SERVER_SIDE_INCLUDE_SERVER_SIDE_INCLUDES_GENERIC("server-side-include/server-side-includes-generic.txt"),
  SQL_INJECTION_DETECT_GENERICBLIND("sql-injection/detect/GenericBlind.txt"),
  SQL_INJECTION_DETECT_GENERIC_SQLI("sql-injection/detect/Generic_SQLI.txt"),
  SQL_INJECTION_DETECT_MSSQL("sql-injection/detect/MSSQL.txt"),
  SQL_INJECTION_DETECT_MSSQL_BLIND("sql-injection/detect/MSSQL_blind.txt"),
  SQL_INJECTION_DETECT_MYSQL("sql-injection/detect/MySQL.txt"),
  SQL_INJECTION_DETECT_MYSQL_MSSQL("sql-injection/detect/MySQL_MSSQL.txt"),
  SQL_INJECTION_DETECT_ORACLE("sql-injection/detect/oracle.txt"),
  SQL_INJECTION_DETECT_XPLATFORM("sql-injection/detect/xplatform.txt"),
  SQL_INJECTION_EXPLOIT_DB2_ENUMERATION("sql-injection/exploit/db2-enumeration.txt"),
  SQL_INJECTION_EXPLOIT_MS_SQL_ENUMERATION("sql-injection/exploit/ms-sql-enumeration.txt"),
  SQL_INJECTION_EXPLOIT_MYSQL_INJECTION_LOGIN_BYPASS("sql-injection/exploit/mysql-injection-login-bypass.txt"),
  SQL_INJECTION_EXPLOIT_MYSQL_READ_LOCAL_FILES("sql-injection/exploit/mysql-read-local-files.txt"),
  SQL_INJECTION_EXPLOIT_POSTGRES_ENUMERATION("sql-injection/exploit/postgres-enumeration.txt"),
  SQL_INJECTION_PAYLOADS_SQL_BLIND_PAYLOADS_SQL_BLIND_MSSQL_INSERT("sql-injection/payloads-sql-blind/payloads-sql-blind-MSSQL-INSERT.txt"),
  SQL_INJECTION_PAYLOADS_SQL_BLIND_PAYLOADS_SQL_BLIND_MSSQL_WHERE("sql-injection/payloads-sql-blind/payloads-sql-blind-MSSQL-WHERE.txt"),
  SQL_INJECTION_PAYLOADS_SQL_BLIND_PAYLOADS_SQL_BLIND_MYSQL_INSERT("sql-injection/payloads-sql-blind/payloads-sql-blind-MySQL-INSERT.txt"),
  SQL_INJECTION_PAYLOADS_SQL_BLIND_PAYLOADS_SQL_BLIND_MYSQL_ORDER_BY("sql-injection/payloads-sql-blind/payloads-sql-blind-MySQL-ORDER_BY.txt"),
  SQL_INJECTION_PAYLOADS_SQL_BLIND_PAYLOADS_SQL_BLIND_MYSQL_WHERE("sql-injection/payloads-sql-blind/payloads-sql-blind-MySQL-WHERE.txt"),
  STRING_EXPANSION_SHELL_EXPANSION("string-expansion/shell-expansion.txt"),
  UNICODE_CORRUPTED("unicode/corrupted.txt"),
  UNICODE_EMOJI("unicode/emoji.txt"),
  UNICODE_JAPANESE_EMOTICON("unicode/japanese-emoticon.txt"),
  UNICODE_NAUGHTY_UNICODE("unicode/naughty-unicode.txt"),
  UNICODE_REGIONALINDICATORS("unicode/regionalindicators.txt"),
  UNICODE_RIGHT_TO_LEFT("unicode/right-to-left.txt"),
  UNICODE_SPECIALCHARS("unicode/specialchars.txt"),
  UNICODE_TWO_BYTE_CHARS("unicode/two-byte-chars.txt"),
  UNICODE_UPSIDEDOWN("unicode/upsidedown.txt"),
  XML_XML_ATTACKS("xml/xml-attacks.txt"),
  XPATH_XPATH_INJECTION("xpath/xpath-injection.txt"),
  XSS_ALL_ENCODINGS_OF_LT("xss/all-encodings-of-lt.txt"),
  XSS_DEFAULT_JAVASCRIPT_EVENT_ATTRIBUTES("xss/default-javascript-event-attributes.txt"),
  XSS_HTML_EVENT_ATTRIBUTES("xss/html-event-attributes.txt"),
  XSS_JHADDIX_XSS_WITH_CONTEXT_DOC("xss/JHADDIX_XSS_WITH_CONTEXT.doc.txt"),
  XSS_XSS_OTHER("xss/xss-other.txt"),
  XSS_XSS_RSNAKE("xss/xss-rsnake.txt"),
  XSS_XSS_URI("xss/xss-uri.txt"),
  XSS_XSSPOLYGLOT("xss/XSSPolyglot.txt");
  /* Developer note: the list above is generated in the build.gradle script and will be regenerated
     during the next build. So please do not edit this list.
   */

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

  public List<String> getData() throws FuzzLoadingException {
    if (data == null) {
      data = new ArrayList<String>();
      URL u = this.getClass().getClassLoader().getResource(filePath);
      InputStream is = this.getClass().getClassLoader().getResourceAsStream(filePath);
      try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
        while (br.ready()) {
          data.add(br.readLine());
        }
      } catch (IOException e) {
        throw new FuzzLoadingException("Can't load data from " + this.filePath, e);
      }
    }
    return data;
  }

}

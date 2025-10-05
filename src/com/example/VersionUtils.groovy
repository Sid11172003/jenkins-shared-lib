// src/com/example/VersionUtils.groovy
package com.example


class VersionUtils implements Serializable {
String bumpPatch(String version) {
def parts = version.tokenize('.').collect { it as int }
parts[-1] = parts[-1] + 1
return parts.join('.')
}


String currentDateString() {
return new Date().format("yyyyMMdd")
}
}

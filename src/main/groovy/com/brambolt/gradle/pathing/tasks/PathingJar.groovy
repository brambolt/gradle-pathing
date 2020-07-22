/*
 * Copyright 2017-2020 Brambolt ehf.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.brambolt.gradle.pathing.tasks

import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.bundling.Jar

class PathingJar extends Jar {

  List<Configuration> included = []

  String mainClass

  @Override
  Task configure(Closure closure) {
    super.configure(closure)
    doFirst {
      manifest {
        attributes(
          'Permissions': 'all-permissions',
          'Build-Date': new Date().format('yyyy-MM-dd HH:mm:ss'),
          "Class-Path": formatClassPath(),
          "Main-Class": mainClass)
      }
    }
    this
  }

  static FileCollection createClassPath(List<Configuration> included) {
    included.tail()
      .inject(included.first()) { Configuration acc, Configuration c -> acc.plus(c) }
  }

  String formatClassPath() {
    createClassPath(included).join(" ")
  }
}

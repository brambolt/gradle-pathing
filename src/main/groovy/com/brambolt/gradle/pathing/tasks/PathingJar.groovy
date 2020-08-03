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

/**
 * This class supports the creation of pathing jars given configurations to
 * form the class path and a main class to execute.
 */
class PathingJar extends Jar {

  /**
   * The configurations that form the pathing jar class path.
   */
  List<Configuration> included = []

  /**
   * The main class for the pathing jar.
   */
  String mainClass

  /**
   * Configures the pathing jar task.
   *
   * <p>The manifest attributes are not computed until execution, to avoid
   * resolving the included configurations during the configuration phase.</p>
   *
   * @param closure The configuration closure for the task
   * @return The configured task
   */
  @Override
  Task configure(Closure closure) {
    super.configure(closure)
    // Avoid resolving the configurations too early:
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

  /**
   * Combines the parameter configurations into a single class path.
   * @param included The configurations to form a class path from
   * @return A file collection comprising the combined class path
   */
  static FileCollection createClassPath(List<Configuration> included) {
    included.tail().inject(included.first()) { Configuration acc, Configuration c -> acc.plus(c) }
  }


  /**
   * Combines the included configurations into a single class path.
   * @param included The configurations to form a class path from
   * @return A class path string to include in the pathing jar
   */
  String formatClassPath() {
    createClassPath(included).join(" ")
  }
}

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

package com.brambolt.gradle

import com.brambolt.gradle.pathing.tasks.PathingJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * <p>A Gradle plug-in for creating pathing jars.</p>
 *
 * @see com.brambolt.gradle.pathing.tasks.PathingJar
 */
class PathingPlugin implements Plugin<Project> {

  /**
   * Applies the plug-in to the parameter project.
   * @param project The project to apply the plug-in to
   */
  void apply(Project project) {
    project.logger.debug("Applying ${getClass().getCanonicalName()}.")
    createPathingJarTask(project)
  }

  protected Task createPathingJarTask(Project project) {
    // Apply an empty configuration closure to set defaults:
    project.task([type: PathingJar], 'pathing') {}
  }
}

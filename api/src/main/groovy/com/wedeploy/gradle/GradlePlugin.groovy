package com.wedeploy.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

class GradlePlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.task('copyDependencies', type: Copy) {
			from(
			project.configurations.compile + project.configurations.runtime) {

				into 'dependencies'
				exclude 'api-*.jar'
				exclude 'server-*.jar'
			}

			into 'build'
		}
	}

}
package org.openbakery.sparkle

import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.openbakery.XcodePlugin
import org.testng.annotations.BeforeMethod
import org.testng.annotations.AfterMethod
import org.testng.annotations.Test

/**
 * Created by Stefan on 25/02/15.
 */
class SparkleReleaseNotesTest {

	File projectDir
	Project project
	SparkleReleaseNotesTask sparkleReleaseNotesTask

	@BeforeMethod
	void setup() {

		projectDir = new File(System.getProperty("java.io.tmpdir"), "gradle-xcodebuild")



		project = ProjectBuilder.builder().withProjectDir(projectDir).build()
		project.buildDir = new File(projectDir, 'build').absoluteFile
		project.apply plugin: org.openbakery.XcodePlugin

		FileUtils.copyFileToDirectory(new File("src/test/Resource/CHANGELOG.md"), projectDir)

		sparkleReleaseNotesTask = project.getTasks().getByPath(XcodePlugin.SPARKLE_NOTES_TASK_NAME)
	}

	@Test
	void checkCreationOfReleaseNotesHTMLFile() {

		sparkleReleaseNotesTask.createReleaseNotes()

		File releaseNotesFile = new File(projectDir.absolutePath + "/build/sparkle/releasenotes.html")

		assert(releaseNotesFile.exists())
	}

	@AfterMethod
	void cleanUp() {
		FileUtils.deleteDirectory(projectDir)
	}
}

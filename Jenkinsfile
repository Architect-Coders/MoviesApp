pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh 'sh gradlew compileDebugSources'
      }
    }
    stage('Unit test') {
      post {
        always {
          junit '**/TEST-*.xml'

        }

      }
      steps {
        sh 'sh gradlew testDebugUnitTest'
      }
    }
    stage('Build APK') {
      steps {
        sh 'sh gradlew assembleDebug'
        archiveArtifacts '**/*.apk'
      }
    }
    stage('Static analysis') {
      steps {
        sh 'sh gradlew lintDebug'
        androidLint(pattern: '**/lint-results-*.xml')
        sh 'sh gradlew detekt'
      }
    }
    stage('Deploy') {
      when {
        branch 'beta'
      }
      environment {
        SIGNING_KEYSTORE = credentials('my-app-signing-keystore')
        SIGNING_KEY_PASSWORD = credentials('my-app-signing-password')
      }
      post {
        success {
          mail(to: 'beta-testers@example.com', subject: 'New build available!', body: 'Check it out!')

        }

      }
      steps {
        sh 'sh gradlew assembleRelease'
        archiveArtifacts '**/*.apk'
      }
    }
  }
  post {
    failure {
      mail(to: 'android-devs@example.com', subject: 'Oops!', body: "Build ${env.BUILD_NUMBER} failed; ${env.BUILD_URL}")

    }

  }
  options {
    skipStagesAfterUnstable()
  }
}
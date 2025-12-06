pipeline {
    agent any

    tools {
        maven 'Maven 3' // Must match the Maven installation name in Jenkins → Global Tool Configuration
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/ashutosapattajoshi/Athena.git', branch: 'main'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Use the Maven tool configured in Jenkins
                    def mvnHome = tool name: 'Maven 3', type: 'maven'
                    sh "${mvnHome}/bin/mvn clean test -Dheadless=true"
                }
            }
        }

        stage('Publish ExtentReports') {
            steps {
                publishHTML([
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'test-output/ExtentReports',
                    reportFiles: 'index.html',
                    reportName: 'Extent HTML Report'
                ])
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'screenshots/*.png', allowEmptyArchive: true
        }
    }
}

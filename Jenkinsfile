pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/ashutosapattajoshi/Athena.git', branch: 'main'
            }
        }

        stage('Run Tests') {
            steps {
                script {
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
            archiveArtifacts artifacts: 'test-output/ExtentReports/**/*', allowEmptyArchive: true 
        }
    }
}

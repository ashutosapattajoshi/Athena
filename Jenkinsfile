pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'  // Match the name configured in Jenkins
        jdk 'Java 17'        // Optional: if you want to specify JDK explicitly
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/ashutosapattajoshi/Athena.git', branch: 'main'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn clean test -Dheadless=true'
            }
        }

        stage('Publish Reports') {
            steps {
                publishHTML([allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'test-output/ExtentReports',
                    reportFiles: 'index.html',
                    reportName: 'Automation Report'])
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

pipeline {
    agent any

    tools {
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
           git branch: 'main',
            url: 'https://github.com/MartynasIT/Selenium-Automation-Framework.git'
            }
        }

        stage('Test') {
            steps {

                 bat "mvn -Dsurefire.suiteXmlFiles=src/test/resources/TestSuites/${params.suite}.xml -Dbrowser=${params.browser} test"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/surefire-reports/TEST-*.xml'
                }
            }
        }
    }
}

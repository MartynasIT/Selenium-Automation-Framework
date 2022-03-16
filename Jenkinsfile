pipeline {
    agent any

    tools {
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                
            }
        }
            
        stage('Test') {
            steps {

                 bat "mvn -Dsurefire.suiteXmlFiles=src/test/resources/TestSuites/LegoSuite.xml -Dbrowser=Chrome test"
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

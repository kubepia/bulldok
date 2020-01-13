def label = "daishin-${UUID.randomUUID().toString()}"

def DOCKER_REGISTRY = 'registry.darumland.net:5000'
def DOCKER_IMAGE = 'sumbank/sam-bulldok' //harbor project name
def IMAGE_VERSION = new Date().format('yyyyMMdd-HHmmss')
// def REGISTRY_CREDENTIALS = 'registry_credential'
// def K8S_NAMESPACE='red'
// def DEPLOYMENT_NAME = 'samstore-deploy'
// def CONTAINER_NAME = 'samstore'

podTemplate(label : label,
    namespace: 'cicd',
    containers: [
        containerTemplate(name: 'maven', image: 'registry.darumland.net:5000/cicd/maven:3.5.2-jdk-8-alpine', ttyEnabled: true, command: 'cat'),
        containerTemplate(name: 'docker', image: 'registry.darumland.net:5000/cicd/docker:latest', ttyEnabled: true, command: 'cat'),
        containerTemplate(name: 'kubectl', image: 'registry.darumland.net:5000/cicd/k8s-kubectl:latest', ttyEnabled: true, command: 'cat')
    ],
    volumes: [
        hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock'),
        persistentVolumeClaim(mountPath: '/root/.m2', claimName: 'localrepo-pvc')
    ]) {
 
    node(label) {
        stage('SOURCE CHECKOUT') {
            def repo = checkout scm
        }
 
        stage('BUILD MAVEN') {
        def configFiles = []
        configFiles.add configFile(fileId:'mavensettings', variable:'MAVEN_SETTINGS')
            container('maven') {
                configFileProvider(configFiles){
                     sh 'mvn clean package -s ${MAVEN_SETTINGS}'
                }
            }
        }
        stage('BUILD DOCKER IMAGE') {
            container('docker') {
                //TODO: Docker image build block
                def build = docker.build(DOCKER_REGISTRY + "/" + DOCKER_IMAGE + ":" + IMAGE_VERSION, ".")
                docker.withRegistry("https://" + DOCKER_REGISTRY, REGISTRY_CREDENTIALS) {
                    build.push()
                }
                docker.withRegistry("https://" + DOCKER_REGISTRY, REGISTRY_CREDENTIALS) {
                    build.push('latest')
                }
                // save image digest
                NEW_IMAGE_DIGEST = sh (
                    script: 'docker inspect --format=\'{{index .RepoDigests 0}}\' ' + DOCKER_REGISTRY + '/' + DOCKER_IMAGE + ':' + IMAGE_VERSION,
                    returnStdout: true
                ).trim()
            }
        }
        stage('K8S Deploy') {
            container('kubectl') {
                sh 'kubectl set image deployment ' + DEPLOYMENT_NAME + ' ' + CONTAINER_NAME + '=' + NEW_IMAGE_DIGEST + ' -n ' + K8S_NAMESPACE 
            }

        }
 
        
    }
}

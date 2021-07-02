import groovy.yaml.YamlSlurper
import com.zepline.Zepline

// def configYaml = '''\
// steps:
// - task: cmd
//   inputs:
//     script: 
//       - echo "hello"
// '''

def config = new YamlSlurper().parse(new File(".zepline.yaml"))
Zepline zepline = new Zepline(config).init()
// zepline.init()

println(zepline.tasks)
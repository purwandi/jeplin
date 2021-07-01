import groovy.yaml.YamlSlurper
import com.zepline.Zepline
import com.zepline.parser.Config

// def configYaml = '''\
// steps:
// - task: cmd
//   inputs:
//     script: 
//       - echo "hello"
// '''

def config = new YamlSlurper().parse(new File(".zepline.yaml"))
Zepline zepline = Config.parse(config)

println(zepline.tasks)
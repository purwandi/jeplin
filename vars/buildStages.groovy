@Library('zepline-ci')

import com.zepline.*

def call(Zepline zepline) {
  return {
    List<String> stagesA = zepline.stages
    stages {
      stagesA.each { stg ->
        stage(stg) {
          sh "echo ${stg}"
        }
      }
    }
  }
}
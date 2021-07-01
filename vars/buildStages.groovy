import com.zepline.Zepline

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
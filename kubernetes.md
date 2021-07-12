## Kekurangan Kubernetes

- tidak bisa menghandle apabila job butuh native mesin seperti macos untuk proses
  build ios
- tidak bisa di parse menggunakan zepline karena api template berbeda jauh dengan
  jenkins job pada umumnya
- hanya `pipeline` yang bisa dicover pada shared library https://www.jenkins.io/doc/book/pipeline/shared-libraries/#defining-declarative-pipelines

## Workarround 

- Manual declare podtemplate di jenkins
- Akan ada repetitive code apabila dipaksanakan menggunakan ini
def only = ["develop", "master", "tags"]
def master = "remotes/origin/develop"
def tags = "tags/v1.0.0"

println tags.replace("remotes/origin/", "")
println tags.contains("tags")

println only.contains("master")


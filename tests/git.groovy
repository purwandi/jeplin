def env = [
  "CI_GIT_COMMIT_SHA": "git --no-pager show -s --format='%H'",
  "CI_GIT_COMMIT_SHORT_SHA": "git --no-pager show -s --format='%h'",
  "CI_GIT_AUTHOR_NAME": "git --no-pager show -s --format='%an'",
  "CI_GIT_AUTHOR_EMAIL": "git --no-pager show -s --format='%ae'",
  "CI_GIT_COMMIT": "git --no-pager show -s --format='%s'",
]
package com.zepline

def checkoutFrom(repo) {
  git url "git@github.com/${repo}"
}

return this
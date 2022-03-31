refresh-branch:
	git pull origin main --prune
	git branch --merged|egrep -v '\*|develop|master'|xargs git branch -d

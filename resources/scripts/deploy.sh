#!/bin/bash
set -e


if [ -z "$TARGET_HOST" ]; then
echo "TARGET_HOST is required"
exit 1
fi


echo "Deploying to $TARGET_HOST..."
# Placeholder: add real deployment steps (rsync/ssh/kubectl etc.)
ssh "$TARGET_HOST" 'mkdir -p /opt/apps/my-app && echo "deployed" > /opt/apps/my-app/deployed.txt'

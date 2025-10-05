// vars/notify.groovy


def call(String message, Map opts = [:]) {
echo "NOTIFY: ${message}"
// Example: extend to post to Slack/email using opts.slackWebhook etc.
if (opts.slackWebhook) {
echo "(placeholder) would send to Slack webhook: ${opts.slackWebhook}"
}
}

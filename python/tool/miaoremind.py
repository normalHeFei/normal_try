from apscheduler.schedulers.background import BackgroundScheduler
import itchat
from datetime import datetime

nick_name = u'小喵'

remind_msg = u'robot:又到了每日监督吃药时间  : )'
re_remind_msg = u'robot: 别忘了回复下我哦.'
success_reply_msg = '好的亲爱的'
success_msg = u'robot: 都不用提醒了嘛, 哈哈哈哈'
fail_msg = u'robot:我只是监督小喵吃药的机器人,不太能理解你说的话'

scheduler = BackgroundScheduler()

cache = {
    'username': '',
    'reply': False
}


def send_msg(msg):
    if not cache['username']:
        cache['username'] = itchat.search_friends(name=nick_name)[0]['UserName']
    itchat.send_msg(msg, cache['username'])


def remind():
    cache['reply'] = False
    send_msg(remind_msg)
    print('到点,提醒吃药')
    scheduler.add_job(re_remind, 'interval',  seconds=30, id='re_remind_job')


def re_remind():
    send_msg(re_remind_msg)
    print('每隔15秒发送')


scheduler.add_job(remind, 'cron', day_of_week='0-6',
                  hour=11, minute=50, second=0)
scheduler.start()


@itchat.msg_register(itchat.content.TEXT)
def print_content(msg):
    text = msg['Text']
    print('%s, %s' % (msg['FromUserName'], cache['username']))
    if msg['FromUserName'] != cache['username']:
        return
    if cache['reply']:
        return
    if text == success_reply_msg:
        scheduler.remove_job('re_remind_job')
        cache['reply'] = True
        print('15秒job已取消')
        send_msg(success_msg)
        return
    send_msg(fail_msg)


itchat.auto_login()

itchat.run()

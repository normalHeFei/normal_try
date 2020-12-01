setTimeout(function(){
    console.log('callee: ', arguments.callee)
    setTimeout(arguments.callee, 1)
}, 1)

$(document).ready(function() {
    let sliderWidth = $('#slider').width();
    let slideList = $('#slideWrap');
    let count = 1;
    let items = slideList.find('li').length;
    let prev = $('#prev');
    let next = $('#next');
    let timer;

    $(window).resize(function() {
        sliderWidth = $('#slider').width();
    });

    let prevSlide = function() {
        if (count > 1) {
            count = count - 2;
            slideList.css('left', '-' + count * sliderWidth + 'px');
            count++;
        } else if (count === 1) {
            count = items - 1;
            slideList.css('left', '-' + count * sliderWidth + 'px');
            count++;
        }
    };

    let nextSlide = function() {
        if (count < items) {
            slideList.css('left', '-' + count * sliderWidth + 'px');
            count++;
        } else if (count === items) {
            slideList.css('left', '0px');
            count = 1;
        }
    };

    let startTimer = function() {
        timer = setTimeout(() => {
            timer = setInterval(() => nextSlide(), 5000);
        }, 5000);
    };

    let resetTimer = function() {
        clearTimeout(timer);
        startTimer();
    };

    startTimer();

    next.on('click', function() {
        resetTimer();
        nextSlide();
    });

    prev.on('click', function() {
        resetTimer();
        prevSlide();
    });
});

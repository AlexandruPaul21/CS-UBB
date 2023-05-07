let responsiveSlider = function () {
    let slider = document.getElementById("slider");
    let sliderWidth = slider.offsetWidth;
    let slideList = document.getElementById("slideWrap");
    let count = 1;
    let items = slideList.querySelectorAll("li").length;
    let prev = document.getElementById("prev");
    let next = document.getElementById("next");

    let timer;

    window.addEventListener('resize', function () {
        sliderWidth = slider.offsetWidth;
    });

    let prevSlide = function () {
        if (count > 1) {
            count = count - 2;
            slideList.style.left = "-" + count * sliderWidth + "px";
            count++;
        } else if (count === 1) {
            count = items - 1;
            slideList.style.left = "-" + count * sliderWidth + "px";
            count++;
        }
    };

    let nextSlide = function () {
        if (count < items) {
            slideList.style.left = "-" + count * sliderWidth + "px";
            count++;
        } else if (count === items) {
            slideList.style.left = "0px";
            count = 1;
        }
    };

    let startTimer = function () {
        timer = setTimeout(() => {
            timer = setInterval(() => nextSlide(), 5000);
        }, 5000);
    };

    let resetTimer = function () {
        clearTimeout(timer);
        startTimer();
    };

    startTimer();

    next.addEventListener("click", function () {
        resetTimer();
        nextSlide();
    });

    prev.addEventListener("click", function () {
        resetTimer();
        prevSlide();
    });
};

window.onload = function () {
    responsiveSlider();
}

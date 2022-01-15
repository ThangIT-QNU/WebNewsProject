

$("document").ready(function(){
    //breaking news slide
    $(".text-container > div:gt(0)").hide();

    setInterval(function () {
        $('.text-container > div:first')
            .fadeOut(1000)
            .next()
            .fadeIn(1000)
            .end()
            .appendTo('.text-container');
    }, 4000);

    //login button
    $(".login-model").hide()

    $(".login-btn").click(function () {
        $(".login-model").toggle(500)
    })

    //profile

    $(".model-profile").hide()
    $(".profile-container").hide()

    $(".btn-profile").click(function () {
        $(".login-model").hide("fast")
        $(".model-profile").show()
        $(".profile-container").slideDown("1000")
    })

    $(".close-profile").click(function () {
        setTimeout(function () {
            $(".model-profile").hide()
        }, 900)
        $(".profile-container").slideUp("1000")
    })

    //more category

    // $(".more-category").onmouseover(function () {
    //     alert("hello")
    //     //
    // })
    $(".model").hover(function () {
        $(".more-category").addClass("active")
        $(".model").show();
    },function () {
        $(".more-category").removeClass("active")
        $(".model").hide();
    })

    $(".model").click(function (){
        // $("li[class^='my-nav-item'].active").removeClass("active")
        localStorage.setItem("navActive", null)
    })

    $(".more-category").hover(
        function (){
            $(".more-category").addClass("active")
            $(".model").show("fast","swing");
        },
        function () {
            $(".more-category").removeClass("active")
            $(".model").hide();
        }
    )



    $("li[class^='my-nav-item']").click(function () {
        $("li[class^='my-nav-item'].active").removeClass("active")
        localStorage.setItem("navActive", $(this).attr("class"))
    })

    // slider
    $(".slides > div:gt(0)").hide();

    setInterval(function () {
        $('.slides > div:first')
            .fadeOut(1000)
            .next()
            .fadeIn(1000)
            .end()
            .appendTo('.slides');
    }, 5000);

    //tab sidebar

    $(".tabcontent li:not(:first-child)").hide()

    $(".tabbar li").click(function () {
        $(".tabbar li").removeClass("active")
        $(this).addClass("active")

        var tabcontent = $(this).attr("href")

        console.log(tabcontent)
        $(".tabcontent li").hide()
        $(tabcontent).show()
    })

    //handle slide show

    const slides = $(".event-slide")
    const prevBtn = $(".prev")
    const nextBtn = $(".next")
    const balls = $(".ball")

    $(".event-slide:first-child").addClass("active")
    $(".ball:first-child").addClass("active")

    // handle when click next button
    nextBtn.click(function () {
        let nextSlide = $(".event-slide.active").next()
        let currentSlide = $(".event-slide.active")
        let currentIndexSlide = currentSlide.index() + 1;

        if (nextSlide.length != 0) {
            currentSlide.addClass("hide-slide-left").one('webkitAnimationEnd', function (event) {
                $(".hide-slide-left").removeClass("hide-slide-left active")
            })
            nextSlide.addClass("active show-slide-right").one('webkitAnimationEnd', function (event) {
                $(".show-slide-right").removeClass("show-slide-right")
            })

            $(".ball.active").removeClass("active")

            $(".ball:nth-child(" + (currentIndexSlide + 1) + ")").addClass("active")

        } else {
            currentSlide.addClass("hide-slide-left").one('webkitAnimationEnd', function (event) {
                $(".hide-slide-left").removeClass("hide-slide-left active")
            })

            $(".event-slide:first-child").addClass("active show-slide-right").one('webkitAnimationEnd', function (event) {
                $(".show-slide-right").removeClass("show-slide-right")
            })

            $(".ball.active").removeClass("active")

            $(".ball:first-child").addClass("active")
        }
    })

    //handle when click prev button

    prevBtn.click(function () {
        clearInterval(autoSlide)
        let prevSlide = $(".event-slide.active").prev()
        let currentSlide = $(".event-slide.active")

        let currentIndexSlide = currentSlide.index() + 1

        console.log(currentIndexSlide)


        if (prevSlide.length != 0) {
            currentSlide.addClass("hide-slide-right").one('webkitAnimationEnd', function (event) {
                $(".hide-slide-right").removeClass("hide-slide-right active")
            })
            prevSlide.addClass("active show-slide-left").one('webkitAnimationEnd', function (event) {
                $(".show-slide-left").removeClass("show-slide-left")
            })

            $(".ball.active").removeClass("active")

            $(".ball:nth-child(" + (currentIndexSlide - 1) + ")").addClass("active")
        } else {
            currentSlide.addClass("hide-slide-right").one('webkitAnimationEnd', function (event) {
                $(".hide-slide-right").removeClass("hide-slide-right active")
            })
            $(".event-slide:last-child").addClass("active show-slide-left").one('webkitAnimationEnd', function (event) {
                $(".show-slide-left").removeClass("show-slide-left")
            })

            $(".ball.active").removeClass("active")

            $(".ball:last-child").addClass("active")
        }


    })


    // handle auto slide

    var autoSlide = setInterval(() => {
        nextBtn.click()
    }, 4000);

    //handle when click ball

    balls.click(function () {

        clearInterval(autoSlide)

        let ballIndex = $(this).index() + 1
        let currentBallIndex = $(".ball.active").index() + 1;

        $(".ball.active").removeClass("active")
        $(this).addClass("active")


        if (ballIndex > currentBallIndex) {
            console.log(ballIndex)
            $(".event-slide.active").addClass("hide-slide-left").one('webkitAnimationEnd', function (event) {
                $(".hide-slide-left").removeClass("hide-slide-left active")
            })
            $(".event-slide:nth-child(" + (ballIndex) + ")").addClass("active show-slide-right").one('webkitAnimationEnd', function (event) {
                $(".show-slide-right").removeClass("show-slide-right")
            })

        } else {
            console.log(ballIndex)
            $(".event-slide.active").addClass("hide-slide-right").one('webkitAnimationEnd', function (event) {
                $(".hide-slide-right").removeClass("hide-slide-right active")
            })
            $(".event-slide:nth-child(" + (ballIndex) + ")").addClass("active show-slide-left").one('webkitAnimationEnd', function (event) {
                $(".show-slide-left").removeClass("show-slide-left")
            })

        }

    })


    $('#autoWidth').lightSlider({
        autoWidth: true,
        loop: true,
        onSliderLoad: function () {
            $('#autoWidth').removeClass('cS-hidden');
        }
    });


    $(".sroll-top-btn").hide()

    $(window).scroll(function () {
        var scroll = $(this).scrollTop()
        $(".login-model").hide()

        if (scroll > 150) {
            $(".navigation").addClass("scroll-active")
            $(".sroll-top-btn").show("slow")
        } else {
            $(".navigation").removeClass("scroll-active")
            $(".sroll-top-btn").hide("slow")
        }
    });

    $(".sroll-top-btn").click(function () {
        $("html, body").animate({scrollTop: 0}, 500)
    })

    const searchInput = document.querySelector(".search form input[name='search-bar']")

    const url = document.location.toString();
    const x = url.lastIndexOf("webnews/")
    const domain = url.slice(0,x)


    searchInput.addEventListener("keyup", function (){
        let keyword = searchInput.value
        let searchLink = domain+"webnews/search?keyword="+keyword

        const httpRequest = new XMLHttpRequest()
        httpRequest.open("GET",searchLink, true)
        httpRequest.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                let html = "";
                let results = JSON.parse(this.responseText)
                for (id in results){
                    let sortTitle = results[id].slice(0,60) + "..."
                    html+= `<li><a href="/webnews/post/${id}"> ${sortTitle} </a></li>`
                }
                document.querySelector(".autocomplete-box").innerHTML = html;
            }
        };
        httpRequest.send()
    })


    let myform =document.getElementById("user-search")
    let searchBtn = document.querySelector(".search-btn")

    searchBtn.addEventListener("click",function (){
        localStorage.setItem("navActive",null)
        let keyword = searchInput.value
        if (keyword.length > 0){
            myform.submit()
        }
    })

})
window.addEventListener("load",function (){
    const loader = document.querySelector(".loader")
    loader.className += " hide";
})



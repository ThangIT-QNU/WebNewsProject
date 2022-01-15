$(document).ready(function(){
    $(".sub-menu-user").hide()
    $(".login-manager").click(function(){
        $(".sub-menu-user").toggle("fast");
    })

    $('#autoWidth').lightSlider({
        autoWidth:true,
        loop:true,
        onSliderLoad: function() {
            $('#autoWidth').removeClass('cS-hidden');
        } 
    });



    $('#myTab a').on('click', function (e) {
        e.preventDefault()
        $('#myTab a').removeClass("active")
        $(this).addClass("active")

        var tab = $(this).attr("href")
        $(".tab-pane").removeClass("show active")
        $(tab).addClass("show active")
      })

})

$(document).ready(function (){
    $('#fileImage').change(function (){
        showImageThumbnail(this)
    })

    function showImageThumbnail(fileInput){
        file = fileInput.files[0]
        reader = new FileReader();

        reader.onload = function(e){
            $('#thumnail').attr("src",e.target.result)
        }

        reader.readAsDataURL(file)
    }

    $(".model-profile").hide()
    $(".profile-container").hide()

    $(".btn-profile").click(function (){
        $(".sub-menu-user").hide("fast")
        $(".model-profile").show()
        $(".profile-container").slideDown("1000")
    })

    $(".close-profile").click(function (){
        setTimeout(function (){
            $(".model-profile").hide()
        },900)
        $(".profile-container").slideUp("1000")
    })

})
window.addEventListener("load",function (){
    const loader = document.querySelector(".loader")
    loader.className += " hide";
})
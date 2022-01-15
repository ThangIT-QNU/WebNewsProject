window.onload = function (){
    $("form[name='form-cmt']").keydown(function(event){
        if(event.keyCode == 13) {
            event.preventDefault();
            return false;
        }
    });
    const likeBtn = document.querySelector(".like-btn")
    let isliked = document.querySelector(".like-btn.isLike") ? true : false;

    likeBtn.addEventListener("click",function (){
        if (isliked){
            likeBtn.classList.remove("isLike")
            isliked = false

            let url = window.location.href+"/like?status=like"
            let xhr =  new XMLHttpRequest()
            xhr.onload = function() {
                document.querySelector(".like-number").innerHTML = this.responseText;
            }
            xhr.open("get",url,true)
            xhr.send()
        }else{
            likeBtn.classList.add("isLike")
            isliked = true

            let url = window.location.href+"/like?status=nolike"
            let xhr =  new XMLHttpRequest()
            xhr.onload = function() {
                document.querySelector(".like-number").innerHTML = this.responseText;
            }
            xhr.open("get",url,true)
            xhr.send()
        }
    })

    const sendBtn = document.querySelector(".send-cmt")

    sendBtn.addEventListener("click", function (){
        let commentContent = document.forms["form-cmt"]["comment"].value
        let url = window.location.href+"/comment?content="+commentContent
        let xhr =  new XMLHttpRequest()
        xhr.onload = function() {
            $(".recently-comment").prepend(this.responseText)
        }
        xhr.open("get",url,true)
        xhr.send()
        document.forms["form-cmt"]["comment"].value="";
        setTimeout(function (){
            var dates = document.querySelector("#justCommentTime")
            dates.innerText = moment(dates.innerText).fromNow()
        },50)
    })


    document.querySelector(".share-btn a").setAttribute("href",`https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(document.URL)}&amp;src=sdkpreparse`)
}

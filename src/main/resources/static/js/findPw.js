let token = document.querySelector("meta[name='_csrf']").getAttribute("content");
let header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

const findForm = document.querySelector("#findForm");
findForm.addEventListener("submit", findPw);

async function findPw(){
    console.log("실행");

    let loginId = document.querySelector("#loginId").value;
    let email = document.querySelector("#email").value;

    if( loginId === null || loginId.trim().length == 0 ){
        alert("아이디를 입력해 주시기 바랍니다.");
    }

    if( email === null || email === "" ){
        alert("이메일을 입력해 주시기 바랍니다.")
    }

    return false;

    let data = {

        method = "POST",
        body: JSON.stringify(
            {
                loginId : loginId,
                email : email,
            }
        ),
        header: {
        'Content-Type' : 'application/json'
        'X-CSRF-TOKEN' : token
        }

    }

    await fetch("http://localhost:8085/mails/find/pw", data)
    .then(
        (response) => {
            return response.json();
        }
    )
    .then(
        (data) => {

        if( !data ){
            alert("이메일 발송 실패, 이메일을 확인해 주시기 바랍니다.");
            return;
        }else{
            alert("발급된 임시 비밀번호를 입력하신 이메일로 보냈습니다.")
            window.location.replace("http://localhost:8085/")
        }
        }
    )
    .catch(
        (error) => {
            console.log(error);
            alert("메일 발송에 실패하였습니다. 이메일 혹은 아이디를 확인하여 주시기 바랍니다.");

        }
    )

}
/*
let token = document.querySelector("meta[name='_csrf']").getAttribute("content");
let header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

const findForm = document.querySelector("#findForm");
findForm.addEventListener("submit", findPw);


async function findPw(){
    console.log("작동되나");

    let loginId = document.querySelector("#loginId");
    let email = document.querySelector("#email");

    if( loginId === null || loginId=== ""){
        alert("아이디룰 입력해주세요");
    }

    if( email === null || email=== ""){
        alert("이메일을 입력해주세요");
    }

    let data = {
        method: "POST",
        body: JSON.Stringify(
            {
                loginId:loginId,
                email:email
            }
        ),
        header: {
            'Content-Type' : 'application/json',
            'X-CSRF-TOKEN': token,
        }
    }

    await fetch(
        "http://localhost:8086/mails/find/pw", data
    ).then(
        (res) => return res.json();
    ).then(
        (data) => {
            if( !data ){
                alert("이메일 발송 실패");
            } else {
                alert("발급된 임시비밀번호를 이용해 로그인해주세요.");
                window.location.replace("http://localhost:8086/");
            }
        }
    ).catch(
        (error) => console.log(error);
    )
}
*/
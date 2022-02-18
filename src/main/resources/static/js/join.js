// Java Script

//전역변수이기에 대문자로 작성
let CHECK_STATUS = false;
let LOGIN_ID_STATUS = false;
let NICKNAME_STATUS = false;


// 닉네임 중복 체크
async function checkDupleNickname(){
    console.log("닉네임 중복 체크 시작");

    let inputNickname = document.querySelector("#nickname");
    let nickname = inputNickname.value;

    console.log(nickname);

    await fetch(
        "http://localhost:8086/members/check/nickname?nickname="+nickname
    ).then(
        (response) => {
//            console.log(response)
            return response.json();
        }
    ).then(
        (data) => {
            let isCheck = data;
            console.log(data.status)
            if(nickname === "" || data.status){
                NICKNAME_STATUS = false;
                alert("사용할 수 없는 닉네임입니다.");
            } else {
                NICKNAME_STATUS = true;
                alert("사용 가능한 닉네임입니다.");
            }
        }
    ).catch(
        (error) => {console.log(error)}
    )
}

// 아이디 중복확인 체크
async function checkDupleLoginId(){
    console.log("아이디 중복 체크 시작")

    let inputLoginId = document.getElementById("loginId");
    let loginId = inputLoginId.value;

    const config = {
//        method: 'POST',
//        body: JSON.stringify({"loginId":loginId})
        headers: {'Content-Type': 'application/json'},
    };

    await fetch(
        "http://localhost:8086/members/check/id?loginId=" + loginId,
        config
    ).then(
        (response) => {
//            console.log("response");
//            console.log(response);
//            console.log("response.json");
//            console.log(response.json);
            return response.json();
        }
    ).then(
        (data) => {
            let idCheck = data;
            console.log("idCheck");
            console.log(idCheck);
            console.log(idCheck.status);
            if(idCheck.status || loginId === "" ){
                LOGIN_ID_STATUS = false;
                alert("가입하실 수 없는 id 입니다.")
            } else {
                LOGIN_ID_STATUS = true;
                alert("사용이 가능한 id 입니다.");
            }

            console.log("LOGIN_ID_STATUS");
            console.log(LOGIN_ID_STATUS);
        }
    ).catch(
        (error) => {
            console.log(error);
        }
    )
}


// 회원가입시 아이디 중복확인 했는지 확인
function checkStatus(){
    if(LOGIN_ID_STATUS === true && NICKNAME_STATUS === true){
        CHECK_STATUS = true;
    } else {
        CHECK_STATUS = false;
    }

    console.log("CHECK_STATUS");
    console.log(CHECK_STATUS);

    if(!LOGIN_ID_STATUS) {
        alert("아이디 중복확인을 해주시길 바랍니다.");
        return false;
    }

    if(!NICKNAME_STATUS){
        alert("닉네임 중복확인을 해주시길 바랍니다.");
        return false;
    }


    return true;
}

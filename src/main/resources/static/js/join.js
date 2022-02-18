// Java Script

let CHECK_STATUS = false; //전역변수이기에 대문자로 작성
let LOGIN_ID_STATUS = false;


//중복확인 체크
async function checkDupleLoginId(){
    console.log("시작")

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
    if(LOGIN_ID_STATUS === true){
        CHECK_STATUS = true;
    } else {
        CHECK_STATUS = false;
    }

    console.log("CHECK_STATUS");
    console.log(CHECK_STATUS);

    if(!CHECK_STATUS) {
        alert("중복확인을 해주시길 바랍니다.");
        return false;
    }

    return true;
}

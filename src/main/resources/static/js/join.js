// Java Script

let CHECK_STATUS = false; //전역변수이기에 대문자로 작성

async function checkDupleLoginId(){

    console.log("시작")

    let inputLoginId = document.getElementById("loginId");
    let loginId = inputLoginId.value;

    const config = {
//        method: 'POST',
        headers: {'Content-Type': 'application/json'},
//        body: JSON.stringify({"loginId":loginId})
    };

    await fetch(
        "http://localhost:8086/members/check/id?loginId=" + loginId,
        config
    ).then(res =>{
        return res.json();
        console.log("Z");
    }).then(res => {

        (data) => {
            console.log(data);
            console.log("check");
        }
//        CHECK_STATUS = true;

    })
    .catch((error) => {
            console.log(error);
    })

}

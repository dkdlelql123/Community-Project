let token = document.querySelector("meta[name='_csrf']").getAttribute("content");

async function removeMember(loginId){

    if( !window.confirm("정말 탈퇴하시겠습니까? 탈퇴 후 정보 복구가 불가능합니다.") ){
        return;
    }

    let data = {
        method : "DELETE" ,
        body: JSON.stringify(
                {
                    loginId : loginId
                }
        ),
        headers : {
          "Content-Type" : "text/plain",
          "X-CSRF-TOKEN" : token
        },
    };

   console.log(data)

    await fetch(
        "http://localhost:8086/members", data
    ).then(
        (response) => {
            return response.json();
        }
    ).then(
        (data) => {
            if( !data ){
                console.log(data)
                alert("탈퇴에 실패했습니다");
                return;
            }

            alert("성공적으로 탈퇴되었습니다");
            window.location.replace("/");
        }
    ).catch(
        (error) => { console.log(error) }
    )

}
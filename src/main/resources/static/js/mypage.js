let token = document.querySelector("meta[name='_csrf']").getAttribute("content");

async function removeMember(loginId){

    if( !window.confirm("정말 탈퇴하시겠습니까? 탈퇴 후 정보 복구가 불가능합니다.") ){
        return;
    }

    let data = {
        method : "DELETE" ,
        body: loginId,
        headers : {
          "Content-Type" : "text/plain",
          "X-CSRF-TOKEN" : token
        }
    };

   console.log(data)

    await fetch(
        "http://localhost:8086/members", data
    ).then(
        (response) => {

                console.log(response);
                return response.json();

        }
    ).then(
        (data) => {
            console.log(data);
            if( !data ){
                alert("탈퇴에 실패하였습니다. 다시 시도해 주시기 바랍니다.");
                return;
            }

            alert("성공적으로 탈퇴되었습니다.");
            window.location.replace("http://localhost:8086/");
        }
    ).catch(
         (error) => {
             console.log(error)
             alert("탈퇴에 실패하였습니다. 다시 시도해 주시기 바랍니다.")
         }
     )
}
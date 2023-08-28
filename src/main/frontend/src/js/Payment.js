
export default function Payment() {

    const { IMP } = window;
    const code = 'imp14397622'
    IMP.init(code); // 'imp00000000' 대신 발급받은 가맹점 식별코드를 사용합니다.

    const payment = () =>{
        IMP.request_pay({
            pg: "html5_inicis",
            pay_method: "card",
            escrow: true,
            merchant_uid: "test_llutsw32",
            name: "테스트 결제",
            amount: 100,
            language: "ko",
            popup: true,
            buyer_name: "username",
            buyer_tel: "010-0000-0000",
            buyer_email: "qwer@naver.com",
            buyer_addr: "금광로11",
            buyer_postcode: "13181",
            m_redirect_url: "",
            notice_url: "",
        });
    }

    return(
        <div onClick={payment}>결제하기</div>
    )

};

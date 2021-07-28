# CMDTalk
> 이모티콘 사용이 가능한 CL 기반 채팅 프로그램 (2019)

> 네트워크 프로그래밍 (2019) - 기말 프로젝트

## 💻 Techs
### SSLSocket
- Server : SSLServerSocket + SSLServerSocketFactory
- Client : SSLSocket + MyFactory(Customized Factory)

### RMI
- 이모티콘 기능 구현

## 📌 How to use?
### 컴파일 방법
 - 인증키는 생성되어있습니다. 
 - 이클립스를 열어 Server.java의 runRoot 변수를 본인의 경로로 바꿉니다. 
 - 저장을 해서 자동 컴파일을 합니다. 

### 동작 과정
 - start rmiregistry
 - 서버를 먼저 실행 시킵니다.  java Server [port]
	ex. java Server 8001
 - 클라이언트를 실행 시킵니다. java Client [serverAddress] [port] [keypass]
	ex. java Client 192.168.56.1 8001 killthislove
 - 클라이언트들이 서로 채팅을 시작합니다. 
 - 이모티콘을 보내고싶을 경우 <웃음> 과 같이 사용하면 됩니다. 
 - 채팅에서 나가고 싶을 경우 time out이 날 때까지 기다리거나 <그럼이만> 을 입력하면 됩니다. 

## 📷 Demo Screen Shot
### Client1
![image](https://user-images.githubusercontent.com/45311765/127265183-ad0951bb-a293-41bb-b275-dbd54527b57f.png)
### Client2
![image](https://user-images.githubusercontent.com/45311765/127265218-a8669c5d-87ce-4978-b0c7-1b7002f57306.png)


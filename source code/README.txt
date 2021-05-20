1. 컴파일 방법
 - 인증키는 생성되어있습니다. 
 - 이클립스를 열어 Server.java의 runRoot 변수를 본인의 경로로 바꿉니다. 
 - 저장을 해서 자동 컴파일을 합니다. 


2. 동작 과정
 - start rmiregistry
 - 서버를 먼저 실행 시킵니다.  java Server [port]
	ex. java Server 8001
 - 클라이언트를 실행 시킵니다. java Client [serverAddress] [port] [keypass]
	ex. java Client 192.168.56.1 8001 killthislove
 - 클라이언트들이 서로 채팅을 시작합니다. 
 - 이모티콘을 보내고싶을 경우 <웃음> 과 같이 사용하면 됩니다. 
 - 채팅에서 나가고 싶을 경우 time out이 날 때까지 기다리거나 <그럼이만> 을 입력하면 됩니다. 

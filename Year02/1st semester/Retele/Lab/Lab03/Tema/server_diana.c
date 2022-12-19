#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#define SIZE 200
 
uint16_t divizori_a(uint16_t a, uint16_t rez[SIZE]) {
   uint16_t k = 0;
   for (uint16_t i = 1; i <= a; i++) {
	if (a % i == 0) {
		rez[k++] = i;
	}
   }
   return k;
}
 
void deservire_client(int c) {
  // deservirea clientului
  uint16_t a, n, div[SIZE];
  recv(c, &a, sizeof(a), MSG_WAITALL);
  a = ntohs(a);
 
  n = divizori_a(a, div);
  n = ntohs(n);
  send(c, &n, sizeof(n), 0);
  n = htons(n);
 
  for (uint16_t i = 0; i <= n; i++) {
	div[i] = htons(div[i]);
        send(c, &div[i], sizeof(div[i]), 0);
  }
 
  close(c);
  // sfarsitul deservirii clientului;
}
 
int main() {
  int s, c, l;
  struct sockaddr_in server, client;
 
  s = socket(AF_INET, SOCK_STREAM, 0);
  if (s < 0) {
    printf("Eroare la crearea socketului server\n");
    return 1;
  }
 
  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;
 
  if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la bind\n");
    return 1;
  }
 
  listen(s, 5);
 
  l = sizeof(client);
  memset(&client, 0, sizeof(client));
 
  while (1) {
    c = accept(s, (struct sockaddr *) &client, (socklen_t *)&l);
    printf("S-a conectat un client.\n");
    if (fork() == 0) { // fiu
      deservire_client(c);
      return 0;
    }
    // se executa doar in parinte pentru ca fiul se termina mai sus din cauza exit-ului    
  }
}

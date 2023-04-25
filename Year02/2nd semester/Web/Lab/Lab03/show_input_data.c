#include <stdio.h>
#include <stdlib.h>
 
int main() {
  printf("Content-type: text/html\n\n");
  printf("Am primit de la browser query string-ul %s", getenv("QUERY_STRING"));
}

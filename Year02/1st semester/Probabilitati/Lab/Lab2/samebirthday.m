function p=samebirthday(n=23,N=1000)
  count=0;
  for i=1:N
    birthdays=randi(365,1,n);
    if length(unique(birthdays))<n
      count++;
    endif
  endfor
  p=count/N;

N=1000;
r=rand(1,N);
y=exprnd(5,1,N).*(r<=0.4)+unifrnd(4,6,1,N).*(r>0.4)
mean(y);
std(y);
mean(y>5);
count1=0; count2=0;
for i=1:N
  r=rand;
  if r<=0.4
    I=1;
    T=exprnd(5);
  else
    I=2;
    T=unifrnd(4,6);
  end
  if T>5
    count1++;
    if I==2
      count2++;
    endif
  endif
endfor
count2/count1

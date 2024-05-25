y=[75.995 91.972 105.711 123.203 131.669 150.697 ...
  179.323 203.212 226.505 249.633 281.422 308.786];
t=(1900:10:2010);

x=(1890:1:2019);
w=[1975,2015];

%fitting exponential
%coeficientii
c=polyfit(t,log(y),1)
K=exp(c(2))
lambda=c(1)

%predictiile
fm=@(t) K*exp(lambda*t);
zs=fm(x);  %evalaure polinom pt x
est=fm(w);  %evalaure polinom pt w


%repr grafica
plot(t,y,'o',x,zs,'-',w,est,'*')
for i=1:length(w)
  text(w(i),est(i)-20,num2str(est(i)))
endfor
title('US Population','FontSize',14)
xlabel('year','FontSize',12)
ylabel('Millions','FontSize',12)


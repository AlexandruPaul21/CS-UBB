y=[75.995 91.972 105.711 123.203 131.669 150.697 ...
  179.323 203.212 226.505 249.633 281.422 308.786];
t=(1900:10:2010);

x=(1890:1:2019);
w=[1975,2015];

%fitting direct
c=polyfit(t,y,3)

mt=mean(t); st=std(t);
%normalizare date
s=(t-mt)/st;
xs=(x-mt)/st;

%fitting cu date normalizate
%coeficientii
cs=polyfit(s,y,3);

%predictiile
zs=polyval(cs,xs);  %evaluare polinom pt x
est=polyval(cs,(w-mt)/st);  %evalaure polinom pt w

%repr grafica
plot(t,y,'o',x,zs,'-',w,est,'*')
for i=1:length(w)
  text(w(i),est(i)-20,num2str(est(i)))
endfor
title('US Population','FontSize',14)
xlabel('year','FontSize',12)
ylabel('Millions','FontSize',12)


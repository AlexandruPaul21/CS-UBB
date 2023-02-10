function mers_cerc_d(p=0.7,k=5,n=4,m=2000)
positions_end=[];
for i=1:m
   pos=mod(mers_axa_a(p,k),n);
   positions_end=[positions_end;pos(end)];
end

N=histc(positions_end,0:n);
bar(0:n,N/m,'hist','FaceColor','b');
set(gca,'XTick',0:n); grid on;
xlim([-1 n+1]);

fprintf('Poz. finale cele mai frecvente: %d.\n',find(N==max(N))-1);
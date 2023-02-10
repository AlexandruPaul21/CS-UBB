clf;grid on; hold on;
n=5; p=1/3; nr_sim=5000;

x=binornd(n,p,1,nr_sim);
N=hist(x,0:n);
bar(0:n,N/nr_sim,'hist','FaceColor','b');  
bar(0:n,binopdf(0:n,n,p),'FaceColor','y');
legend('probabilitatile estimate','probabilitatile teroretice');

set(findobj('type','patch'),'facealpha',0.7);xlim([-1 n+1]);


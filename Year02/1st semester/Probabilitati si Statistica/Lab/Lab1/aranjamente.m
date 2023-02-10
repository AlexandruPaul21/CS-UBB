function aranjamente=aranjamente(v,k)
combinari=nchoosek(v,k);
aranjamente=[ ];
for i=1:nchoosek(length(v),k)
    aranjamente=[aranjamente;perms(combinari(i,:))];
end
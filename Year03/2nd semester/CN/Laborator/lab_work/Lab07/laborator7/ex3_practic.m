time=[0,3,5,8,13];
distance=[0,225,383,623,993];
speed=[75,77,80,74,72];
approx_time=10;


interpolated_value = hermite_interpolation(time, distance, speed, approx_time)
speed=interpolated_value/approx_time





























%{
time_interpolation=linspace(0,13,100);
dd = divdiff_multiple_nodes(time, distance, speed);


distance_interpolation=zeros(size(time_interpolation));
speed_interpolation=zeros(size(time_interpolation));



for i = 1:length(time_interpolation)
    distance_interpolation(i) = hermite_interpolation(time, distance, speed, time_interpolation(i));
    if i>1
      speed_interpolation(i)=(distance_interpolation(i) - distance_interpolation(i-1)) / (time_interpolation(i) - time_interpolation(i-1));
    endif
end


% Plotting results
figure;
subplot(2,1,1);
plot(time, distance, 'o', time_interpolation, distance_interpolation, '-');
title('Hermite Interpolation for Distance vs. Time');
xlabel('Time (s)');
ylabel('Distance (m)');
legend('Data Points', 'Interpolated Curve');

subplot(2,1,2);
plot(time, speed, 'o', time_interpolation(2:end), speed_interpolation(2:end), '-');
title('Estimated Speed vs. Time');
xlabel('Time (s)');
ylabel('Speed (m/s)');
legend('Speed Data Points', 'Estimated Speed');
%}
%}

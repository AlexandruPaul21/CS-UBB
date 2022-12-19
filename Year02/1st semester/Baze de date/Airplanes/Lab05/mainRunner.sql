USE Airlines226

exec crudAirplanes 'delete','1002','Boeing 767','400','YR-BAA','4'

exec crudFlights 'update', '3', '3', '5', '2022-01-14 06:00:00.000', '10000'

exec crudBoardings 'delete', '1', '13'
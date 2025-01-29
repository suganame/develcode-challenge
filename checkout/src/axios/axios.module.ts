import { HttpModule, HttpService } from '@nestjs/axios';
import { Module } from '@nestjs/common';
import { createAxiosInstance } from '../config/axios.config';

@Module({
    imports: [
        HttpModule,
    ],
    providers: [
        {
            provide: HttpService,
            useFactory: () => {
                const instance = createAxiosInstance();
                return new HttpService(instance);
            },
        },
    ],
    exports: [HttpService],
})
export class AxiosModule { }